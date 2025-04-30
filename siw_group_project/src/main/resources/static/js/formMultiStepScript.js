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

function changeStep(n) {
  const input = steps[currentStep].querySelector('input, textarea');
  if (n === 1 && !input.checkValidity()) {
    input.reportValidity();
    return;
  }
  currentStep += n;
  showStep(currentStep);
}

nextBtn.addEventListener('click', () => changeStep(1));
prevBtn.addEventListener('click', () => changeStep(-1));

showStep(currentStep);

document.getElementById('multiStepForm').addEventListener('submit', function (e) {
  e.preventDefault();
  alert('Modulo inviato con successo!');
  // Qui potresti aggiungere fetch/AJAX per inviare i dati a un server
});
