const razzePerSpecie = {
    "Cane": ["Labrador", "Bulldog", "Pastore Tedesco", "Barboncino"],
    "Gatto": ["Persiano", "Siamese", "Maine Coon", "Europeo"],
    "Pappagallo": ["Cacatua", "Ara", "Inseparabile", "Calopsitta"],
    "Roditore": ["Topo", "Cavia", "Porcellino D'India", "Coniglio"],
    "Rettile": ["Tartaruga", "Serpente", "Iguana", "Coccodrillo"]
};

const specieSelect = document.getElementById("specie");
const razzaSelect = document.getElementById("razza");

function updateRazze(specie) {
    razzaSelect.innerHTML = '<option value="">-- Seleziona una razza --</option>';
    if (razzePerSpecie[specie]) {
        razzePerSpecie[specie].forEach(razza => {
            const option = document.createElement("option");
            option.value = razza;
            option.textContent = razza;
            razzaSelect.appendChild(option);
        });
    }
}

specieSelect.addEventListener("change", () => {
    updateRazze(specieSelect.value);
});


document.addEventListener("DOMContentLoaded", () => {
    const currentSpecie = specieSelect.value;
    const currentRazza = razzaSelect.getAttribute("data-selected");
    updateRazze(currentSpecie);
    if (currentRazza) {
        razzaSelect.value = currentRazza;
    }
});


document.getElementById("file").addEventListener('change', function () {
    const file = this.files[0];
    const preview = document.getElementById("preview");
    if (file) {
        const reader = new FileReader();
        reader.onload = e => preview.innerHTML = `<img src="${e.target.result}" style="max-width:300px;">`;
        reader.readAsDataURL(file);
    } else {
        preview.innerHTML = "";
    }
});

const map = L.map('map').setView([41.9028, 12.4964], 13);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap'
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
                const label = place.properties.label || place.properties.name || 'Località';
                const div = document.createElement('div');
                div.textContent = label;
                div.dataset.lat = place.geometry.coordinates[1];
                div.dataset.lon = place.geometry.coordinates[0];
                div.addEventListener('click', () => {
                    input.value = label;
                    document.getElementById('lat').value = div.dataset.lat;
                    document.getElementById('lon').value = div.dataset.lon;
                    if (marker) map.removeLayer(marker);
                    marker = L.marker([div.dataset.lat, div.dataset.lon]).addTo(map).bindPopup(label).openPopup();
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