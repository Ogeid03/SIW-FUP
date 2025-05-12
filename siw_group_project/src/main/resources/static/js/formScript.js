// Funzione per alternare l'espansione delle risposte nelle FAQ
const faqQuestions = document.querySelectorAll('.faq .question');

faqQuestions.forEach(question => {
    question.addEventListener('click', function() {
        const answer = this.nextElementSibling; // La risposta è subito dopo la domanda
        const icon = this.querySelector('.icon'); // L'icona della freccia

        // Alterna la classe 'active' alla domanda
        this.classList.toggle('active');

        // Mostra o nascondi la risposta
        if (answer.style.display === 'block') {
            answer.style.display = 'none';
        } else {
            answer.style.display = 'block';
        }

        // Ruota l'icona
        icon.style.transform = icon.style.transform === 'rotate(45deg)' ? 'rotate(0deg)' : 'rotate(45deg)';
    });
});