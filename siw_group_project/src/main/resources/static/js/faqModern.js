document.addEventListener("DOMContentLoaded", function () {
    const items = document.querySelectorAll('.faq-item');
    items.forEach(item => {
        const question = item.querySelector('.faq-question');
        const answer = item.querySelector('.faq-answer');
        
        if (answer && answer.querySelector('ul')) {
            answer.classList.add('has-list');
        }
        question.addEventListener('click', () => {
            
            items.forEach(i => {
                if (i !== item) i.classList.remove('open');
            });
            
            item.classList.toggle('open');
        });
        question.addEventListener('keydown', (e) => {
            if (e.key === 'Enter' || e.key === ' ') {
                e.preventDefault();
                question.click();
            }
        });
    });
});
