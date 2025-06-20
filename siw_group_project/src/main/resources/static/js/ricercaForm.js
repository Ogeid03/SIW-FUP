function mostra(sezione) {
    document.getElementById("segnalazioni").classList.add("d-none");
    document.getElementById("denunce").classList.add("d-none");
    document.getElementById(sezione).classList.remove("d-none");

    const intro = document.getElementById("intro");
    if (intro) intro.style.display = "none";

    filtraSchede();
}

function filtraSchede() {
    const input = document.getElementById("searchInput").value.toLowerCase();
    const sezioni = ["segnalazioni", "denunce"];

    sezioni.forEach(sezioneId => {
        const sezione = document.getElementById(sezioneId);
        if (sezione.classList.contains("d-none")) return;

        const schede = sezione.getElementsByClassName("scheda");
        let visibili = 0;

        for (let scheda of schede) {
            const testoScheda = scheda.textContent.toLowerCase();
            if (testoScheda.includes(input)) {
                scheda.style.display = "flex";
                visibili++;
            } else {
                scheda.style.display = "none";
            }
        }

        // Gestione messaggi "nessun risultato"
        const msgSegnalazioni = document.getElementById("nessunRisultatoSegnalazioni");
        const msgDenunce = document.getElementById("nessunRisultatoDenunce");
        if (sezioneId === "segnalazioni") {
            if (msgSegnalazioni) msgSegnalazioni.classList.toggle("d-none", visibili > 0);
        } else if (sezioneId === "denunce") {
            if (msgDenunce) msgDenunce.classList.toggle("d-none", visibili > 0);
        }
    });
}


function resetFiltro() {
    document.getElementById("searchInput").value = "";
    filtraSchede();
}