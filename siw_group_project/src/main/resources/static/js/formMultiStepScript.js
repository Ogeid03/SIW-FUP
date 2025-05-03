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
function anteprimaImmagine(url) {
  const preview = document.getElementById("preview");
  if (url && (url.startsWith("http://") || url.startsWith("https://"))) {
      preview.innerHTML = `<img src="${url}" alt="Anteprima" style="max-width:300px; margin-top:10px;">`;
  } else {
      preview.innerHTML = "<p>Nessuna anteprima</p>";
  }
}

// Controllo per il submit del form
document.getElementById("multiStepForm").addEventListener("submit", function(e) {
  const fotoInput = document.getElementById("foto");
  const url = fotoInput.value.trim();

  // Regex base per URL HTTP/HTTPS
  const urlRegex = /^https?:\/\/[^\s$.?#].[^\s]*$/;

  if (url && !urlRegex.test(url)) {
    alert("Inserisci un URL valido per la foto (deve iniziare con http:// o https://)");
    fotoInput.focus();
    e.preventDefault();
  }
});
