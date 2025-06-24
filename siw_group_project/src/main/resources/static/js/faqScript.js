document.addEventListener("DOMContentLoaded", function () {
    const faqs = document.querySelectorAll(".faq");

    faqs.forEach(faq => {
        const question = faq.querySelector(".question");
        const answer = faq.querySelector(".answer");
        const icon = faq.querySelector(".icon");

        question.addEventListener("click", () => {
            const isVisible = answer.style.display === "block";

            
            document.querySelectorAll(".faq .answer").forEach(a => a.style.display = "none");
            document.querySelectorAll(".faq .icon").forEach(i => i.textContent = "+");

            if (!isVisible) {
                answer.style.display = "block";
                icon.textContent = "−";
            }
        });
    });
});

