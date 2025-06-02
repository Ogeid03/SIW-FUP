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
        for (let scheda of schede) {
            const testoScheda = scheda.textContent.toLowerCase();
            scheda.style.display = testoScheda.includes(input) ? "flex" : "none";
        }
    });
}

function resetFiltro() {
    document.getElementById("searchInput").value = "";
    filtraSchede();
}