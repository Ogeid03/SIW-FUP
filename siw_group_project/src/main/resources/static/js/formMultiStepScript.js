let currentStep = 0;
const steps = document.querySelectorAll('.step');
const nextBtn = document.getElementById('nextBtn');
const prevBtn = document.getElementById('prevBtn');
const submitBtn = document.getElementById('submitBtn');

function showStep(n) {
  steps.forEach((step, i) => step.classList.toggle('active', i === n));
  prevBtn.style.display = n === 0 ? 'none' : 'inline-block';
  nextBtn.style.display = n === steps.length - 1 ? 'none' : 'inline-block';
  submitBtn.style.display = n === steps.length - 1 ? 'inline-block' : 'none';
}

function validateStep(index) {
  const step = steps[index];
  const inputs = step.querySelectorAll('input, select, textarea');
  let valid = true;

  inputs.forEach(input => {
    // Trova il messaggio di errore subito dopo l'input, se esiste
    const requiredMessage = input.nextElementSibling && input.nextElementSibling.classList.contains('required-message')
      ? input.nextElementSibling
      : null;

    if (input.hasAttribute('required') && !input.value.trim()) {
      valid = false;
      input.classList.add('invalid');
      if (requiredMessage) requiredMessage.classList.add('active'); // mostra messaggio
    } else {
      input.classList.remove('invalid');
      if (requiredMessage) requiredMessage.classList.remove('active'); // nascondi messaggio
    }
  });

  if (index === 0) {
    const lat = document.getElementById('lat').value;
    const lon = document.getElementById('lon').value;
    if (!lat || !lon) {
      valid = false;
      const luogoInput = document.getElementById('luogo');
      luogoInput.classList.add('invalid');
      suggestionMessage.textContent = 'Seleziona un luogo valido dalla lista.';
      // Se vuoi, puoi mostrare il messaggio obbligatorio sotto "luogo" in modo simile
    } else {
      suggestionMessage.textContent = '';
      document.getElementById('luogo').classList.remove('invalid');
    }
  }

  return valid;
}



function changeStep(n) {
  if (n === 1 && !validateStep(currentStep)) return;
  currentStep += n;
  if (currentStep < 0) currentStep = 0;
  if (currentStep >= steps.length) currentStep = steps.length - 1;
  showStep(currentStep);
}

nextBtn.addEventListener('click', () => changeStep(1));
prevBtn.addEventListener('click', () => changeStep(-1));
showStep(currentStep);

function anteprimaImmagine() {
  const fileInput = document.getElementById("file");
  const preview = document.getElementById("preview");
  const file = fileInput.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = e => preview.innerHTML = `<img src="${e.target.result}" style="max-width:300px;margin-top:10px;">`;
    reader.readAsDataURL(file);
  } else {
    preview.innerHTML = "<p>Nessuna anteprima</p>";
  }
}

document.getElementById("file").addEventListener('change', anteprimaImmagine);

const map = L.map('map').setView([41.9028, 12.4964], 13);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
  attribution: '© OpenStreetMap contributors'
}).addTo(map);

let marker;
const input = document.getElementById('luogo');
const suggestionMessage = document.getElementById('suggestionMessage');
const customDropdown = document.getElementById('customDropdown');

input.addEventListener('input', () => {
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
      results.forEach(place => {
        const label = place.properties.label || 
          [place.properties.name, place.properties.street, place.properties.city, place.properties.country]
            .filter(Boolean).join(', ');
        const div = document.createElement('div');
        div.textContent = label;
        div.dataset.lat = place.geometry.coordinates[1];
        div.dataset.lon = place.geometry.coordinates[0];
        div.addEventListener('click', () => {
          input.value = div.textContent;
          document.getElementById('lat').value = div.dataset.lat;
          document.getElementById('lon').value = div.dataset.lon;
          if (marker) map.removeLayer(marker);
          marker = L.marker([div.dataset.lat, div.dataset.lon]).addTo(map).bindPopup(div.textContent).openPopup();
          map.setView([div.dataset.lat, div.dataset.lon], 15);
          customDropdown.style.display = 'none';
          suggestionMessage.textContent = '';
        });
        customDropdown.appendChild(div);
      });
      customDropdown.style.display = 'block';
    })
    .catch(() => {
      suggestionMessage.textContent = 'Errore nel caricamento dei risultati.';
      customDropdown.style.display = 'none';
    });
});

document.addEventListener('click', e => {
  if (!customDropdown.contains(e.target) && e.target !== input) {
    customDropdown.style.display = 'none';
  }
});
