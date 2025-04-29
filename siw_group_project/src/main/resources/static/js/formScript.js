function showRecap(option) {
    const form = document.getElementById("dynamicForm");
    const inputs = form.querySelectorAll("input");
    let recapHTML = "<h2>Riepilogo dati inseriti</h2><ul>";

    inputs.forEach(input => {
        recapHTML += `<li><strong>${input.previousElementSibling.textContent}</strong>: ${input.value}</li>`;
    });

    recapHTML += "</ul>";
    recapHTML += `<button onclick="submitToBackend('${option}')">CONFERMA</button>`;

    form.innerHTML = recapHTML;
}

function submitToBackend(option) {
    const formData = {};
    document.querySelectorAll("#dynamicForm li").forEach(li => {
        const [label, value] = li.textContent.split(": ");
        formData[label.trim()] = value.trim();
    });

    fetch(`/${option === "option1" ? "segnalazioni" : "denunce"}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(formData)
    })
    .then(response => {
        if (response.ok) {
            alert("Dati inviati con successo!");
            location.reload(); // o redireziona su una pagina di conferma
        } else {
            alert("Errore durante l'invio.");
        }
    });
}
//PULSANTE 
function changeForm(option, btn) {
    const form = document.getElementById("dynamicForm");
    form.innerHTML = ""; // Svuota il form prima di aggiungere nuovi campi

    // Rimuove la classe 'active' da tutti i pulsanti
    document.querySelectorAll(".button-container button").forEach(button => {
        button.classList.remove("active");
    });

    // Aggiunge la classe 'active' al pulsante cliccato
    btn.classList.add("active");

    if (option === "option1") {
        form.innerHTML = `
            <label for="luogo">Luogo Avvistamento:</label>
                <input type="text" id="luogo" placeholder="Inserisci la posizione dell'avvistamento"><br>
            <label for="specie">Specie:</label>
                <input type="text" id="specie" placeholder="es. cane, gatto, pappagallo..."><br>
            <label for="razza">Razza:</label>
                <input type="text" id="razza" placeholder="Se conosci la razza inserisci qui"><br>
            <label for="descrizione">Descrizione Fisica:</label>
                <input type="text" id="descrizione" placeholder="Inserisci segni particolari (es. colore)"><br>
            <label for="salute">Stato Salute Apparente:</label>
                <input type="text" id="salute" placeholder="Come ti sembra stia l'animale?"><br>
            <label for="azione">Azioni Intraprese:</label>
                <input type="text" id="azione" placeholder="Hai agito in qualche modo?"><br>
            <label for="info">Informazioni Extra:</label>
                <input type="text" id="info" placeholder="max 255 caratteri"><br>
            <label for="foto">Foto:</label>
                <input type="text" id="foto" placeholder=""><br>
            <button type="button" onclick="showRecap('option1')">INVIA</button>
        `;
    } else if (option === "option2") {
        form.innerHTML = `
            <label for="luogo">Luogo Avvistamento:</label>
                <input type="text" id="luogo" placeholder="Inserisci la posizione dell'avvistamento"><br>
            <label for="nome">Nome del PET:</label>
                <input type="text" id="nome" placeholder="es. umberto, filippo, andreaEmilcare..."><br>
            <label for="eta">Età del PET:</label>
                <input type="text" id="eta" placeholder="es. 33, 101, 0.003..."><br>
            <label for="specie">Specie:</label>
                <input type="text" id="specie" placeholder="es. cane, gatto, pappagallo..."><br>
            <label for="razza">Razza:</label>
                <input type="text" id="razza" placeholder="Se conosci la razza inserisci qui"><br>
            <label for="descrizione">Descrizione Fisica:</label>
                <input type="text" id="descrizione" placeholder="Inserisci segni particolari (es. colore)"><br>
            <label for="premio">Premio Offerto:</label>
                <input type="text" id="premio" placeholder="Aggiungi una somma in denaro per il riscatto"><br> 
            <label for="info">Informazioni Extra:</label>
                <input type="text" id="info" placeholder="max 255 caratteri"><br>
            <label for="foto">Foto:</label>
                <input type="text" id="foto" placeholder=""><br>
            <button type="button" onclick="showRecap('option2')">INVIA</button>
        `;
    } else if (option === "option3") {
        form.innerHTML = `
            <label for="message">Che cerchi?</label>
            <input type="text" id="message" placeholder="work in progress...">
        `;
    }
}

// Imposta automaticamente il form su "Segnalazioni" quando la pagina si carica
window.onload = function() {
    changeForm('option1', document.getElementById("btn1"));
};

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