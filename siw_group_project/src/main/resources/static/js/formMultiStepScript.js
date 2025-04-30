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

// Gestisci l'invio del form
document.getElementById('multiStepForm').addEventListener('submit', function(e) {
  e.preventDefault();
  alert('Modulo inviato con successo!');
});

showStep(currentStep);
