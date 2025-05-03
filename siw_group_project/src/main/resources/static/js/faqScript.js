document.addEventListener("DOMContentLoaded", function () {
    const faqs = document.querySelectorAll(".faq");

    faqs.forEach(faq => {
        const question = faq.querySelector(".question");
        const answer = faq.querySelector(".answer");
        const icon = faq.querySelector(".icon");

        question.addEventListener("click", () => {
            const isVisible = answer.style.display === "block";

            // Chiude tutte le risposte
            document.querySelectorAll(".faq .answer").forEach(a => a.style.display = "none");
            document.querySelectorAll(".faq .icon").forEach(i => i.textContent = "+");

            // Se non era visibile, apri questa
            if (!isVisible) {
                answer.style.display = "block";
                icon.textContent = "−";
            }
        });
    });
});

