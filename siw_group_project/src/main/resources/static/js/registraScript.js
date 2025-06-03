
const form = document.querySelector("form");
const password = document.getElementById("password");
const confermaPassword = document.getElementById("confermaPassword");
const errorMessage = document.getElementById("passwordError");

form.addEventListener("submit", function (e) {
    if (password.value !== confermaPassword.value) {
        e.preventDefault(); // blocca l'invio
        errorMessage.style.display = "block";
    } else {
        errorMessage.style.display = "none";
    }
});
