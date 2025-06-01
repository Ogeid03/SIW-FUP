let currentStep = 0;
const steps = document.querySelectorAll('.step');
const nextBtn = document.getElementById('nextBtn');
const prevBtn = document.getElementById('prevBtn');
const submitBtn = document.getElementById('submitBtn');

function showStep(n) {
  steps.forEach((step, i) => {
    step.classList.toggle('active', i === n);
  });
  prevBtn.style.display = n === 0 ? 'none' : 'inline-block';
  nextBtn.style.display = n === steps.length - 1 ? 'none' : 'inline-block';
  submitBtn.style.display = n === steps.length - 1 ? 'inline-block' : 'none';
}

function validateStep(stepIndex) {
  const step = steps[stepIndex];
  const inputs = step.querySelectorAll('input, select, textarea');
  let valid = true;
  inputs.forEach(input => {
    if (!input.checkValidity()) {
      valid = false;
      input.classList.add('invalid');
      const msg = input.nextElementSibling;
      if (msg && msg.classList.contains('required-message')) {
        msg.classList.add('active');
      }
    } else {
      input.classList.remove('invalid');
      const msg = input.nextElementSibling;
      if (msg && msg.classList.contains('required-message')) {
        msg.classList.remove('active');
      }
    }
  });
  return valid;
}

function changeStep(n) {
  if (n === 1 && !validateStep(currentStep)) {
    return;
  }
  currentStep += n;
  if (currentStep < 0) currentStep = 0;
  if (currentStep >= steps.length) currentStep = steps.length - 1;
  showStep(currentStep);
}

nextBtn.addEventListener('click', () => changeStep(1));
prevBtn.addEventListener('click', () => changeStep(-1));

showStep(currentStep);

function anteprimaImmagine(input) {
  const file = document.getElementById("file").files[0];
  const preview = document.getElementById("preview");
  if (file) {
    const reader = new FileReader();
    reader.onload = e => {
      preview.innerHTML = `<img src="${e.target.result}" alt="Anteprima" style="max-width:300px; margin-top:10px;">`;
    };
    reader.readAsDataURL(file);
  } else {
    preview.innerHTML = "<p>Nessuna anteprima</p>";
  }
}

document.getElementById("file").addEventListener('change', function() {
  anteprimaImmagine(this);
});

let map = L.map('map').setView([41.9028, 12.4964], 13);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
  attribution: '© OpenStreetMap contributors'
}).addTo(map);

let marker;
const input = document.getElementById('luogo');
const suggestionMessage = document.getElementById('suggestionMessage');
const customDropdown = document.getElementById('customDropdown');

input.addEventListener('input', function () {
  const query = input.value.trim();
  customDropdown.innerHTML = '';
  suggestionMessage.textContent = '';
  if (query.length < 3) {
    customDropdown.style.display = 'none';
    return;
  }
  fetch(`https://photon.komoot.io/api/?q=${encodeURIComponent(query)}&limit=5`)
    .then(res => res.json())
    .then(data => {
      const results = data.features;
      if (results.length === 0) {
        suggestionMessage.textContent = 'Nessun risultato trovato.';
        customDropdown.style.display = 'none';
        return;
      }
      requestAnimationFrame(() => {
        results.forEach(place => {
          const label = place.properties.label ||
            [place.properties.name, place.properties.street, place.properties.city, place.properties.country]
              .filter(Boolean)
              .join(', ');
          const div = document.createElement('div');
          div.textContent = label;
          div.dataset.lat = place.geometry.coordinates[1];
          div.dataset.lon = place.geometry.coordinates[0];
          div.addEventListener('click', function () {
            input.value = div.textContent;
            document.getElementById('lat').value = div.dataset.lat;
            document.getElementById('lon').value = div.dataset.lon;
            if (marker) map.removeLayer(marker);
            marker = L.marker([div.dataset.lat, div.dataset.lon]).addTo(map).bindPopup(div.textContent).openPopup();
            map.setView([div.dataset.lat, div.dataset.lon], 15);
            customDropdown.style.display = 'none';
          });
          customDropdown.appendChild(div);
        });
        customDropdown.style.display = 'block';
      });
    })
    .catch(err => {
      console.error('Errore durante il fetch degli indirizzi:', err);
      suggestionMessage.textContent = 'Errore nel caricamento dei risultati.';
      customDropdown.style.display = 'none';
    });
});

document.addEventListener('click', function (e) {
  if (!customDropdown.contains(e.target) && e.target !== input) {
    customDropdown.style.display = 'none';
  }
});

input.addEventListener('change', function () {
  const selectedOption = Array.from(customDropdown.children)
    .find(option => option.textContent === input.value);
  if (selectedOption) {
    const lat = selectedOption.dataset.lat;
    const lon = selectedOption.dataset.lon;
    document.getElementById('lat').value = lat;
    document.getElementById('lon').value = lon;
    if (marker) map.removeLayer(marker);
    marker = L.marker([lat, lon]).addTo(map).bindPopup(input.value).openPopup();
    map.setView([lat, lon], 15);
  }
});
