// Modern FAQ accordion with animation and accessibility
// Requires: .faq-accordion, .faq-item, .faq-question, .faq-answer, .faq-icon

document.addEventListener("DOMContentLoaded", function () {
    const items = document.querySelectorAll('.faq-item');
    items.forEach(item => {
        const question = item.querySelector('.faq-question');
        question.addEventListener('click', () => {
            // Close all others
            items.forEach(i => {
                if (i !== item) i.classList.remove('open');
            });
            // Toggle this one
            item.classList.toggle('open');
        });
        // Keyboard accessibility
        question.addEventListener('keydown', (e) => {
            if (e.key === 'Enter' || e.key === ' ') {
                e.preventDefault();
                question.click();
            }
        });
    });
});
