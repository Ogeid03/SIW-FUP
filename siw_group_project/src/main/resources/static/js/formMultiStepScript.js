// Variabili per i passi del modulo
let currentStep = 0;
const steps = document.querySelectorAll('.step');
const nextBtn = document.getElementById('nextBtn');
const prevBtn = document.getElementById('prevBtn');
const submitBtn = document.getElementById('submitBtn');

// Funzione per mostrare lo step attuale
function showStep(n) {
  steps.forEach((step, i) => {
    step.classList.toggle('active', i === n);
  });

  prevBtn.style.display = n === 0 ? 'none' : 'inline-block';
  nextBtn.style.display = n === steps.length - 1 ? 'none' : 'inline-block';
  submitBtn.style.display = n === steps.length - 1 ? 'inline-block' : 'none';
}

// Funzione per cambiare lo step
function changeStep(n) {
  currentStep += n;
  if (currentStep < 0) currentStep = 0;
  if (currentStep >= steps.length) currentStep = steps.length - 1;
  showStep(currentStep);
}

// Aggiungi eventi per il pulsante "Avanti" e "Indietro"
nextBtn.addEventListener('click', () => changeStep(1));
prevBtn.addEventListener('click', () => changeStep(-1));

// Mostra il passo iniziale
showStep(currentStep);

// Funzione per l'anteprima dell'immagine
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

// // Controllo per il submit del form
// document.getElementById("multiStepForm").addEventListener("submit", function (e) {
//   const fotoInput = document.getElementById("foto");
//   const url = fotoInput.value.trim();

//   // Regex base per URL HTTP/HTTPS
//   const urlRegex = /^https?:\/\/[^\s$.?#].[^\s]*$/;

//   if (url && !urlRegex.test(url)) {
//     alert("Inserisci un URL valido per la foto (deve iniziare con http:// o https://)");
//     fotoInput.focus();
//     e.preventDefault();
//   }
// });

// Inizializzazione mappa
let map = L.map('map').setView([41.9028, 12.4964], 13);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
  attribution: '© OpenStreetMap contributors'
}).addTo(map);

let marker;
const input = document.getElementById('luogo');
const suggestionMessage = document.getElementById('suggestionMessage');
const customDropdown = document.getElementById('customDropdown');

// Gestione suggerimenti input luogo
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

          // Aggiungi evento click per selezionare il risultato
          div.addEventListener('click', function () {
            input.value = div.textContent;
            document.getElementById('lat').value = div.dataset.lat;
            document.getElementById('lon').value = div.dataset.lon;

            if (marker) map.removeLayer(marker);
            marker = L.marker([div.dataset.lat, div.dataset.lon]).addTo(map).bindPopup(div.textContent).openPopup();
            map.setView([div.dataset.lat, div.dataset.lon], 15);

            customDropdown.style.display = 'none'; // Nascondi il dropdown dopo la selezione
          });

          customDropdown.appendChild(div);
        });

        customDropdown.style.display = 'block'; // Mostra il dropdown se ci sono risultati
      });
    })
    .catch(err => {
      console.error('Errore durante il fetch degli indirizzi:', err);
      suggestionMessage.textContent = 'Errore nel caricamento dei risultati.';
      customDropdown.style.display = 'none';
    });
});

// Nascondi il dropdown quando si clicca fuori
document.addEventListener('click', function (e) {
  if (!customDropdown.contains(e.target) && e.target !== input) {
    customDropdown.style.display = 'none';
  }
});

// Gestione cambio indirizzo selezionato
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
