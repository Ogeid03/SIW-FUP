

function caricaMessaggi() {
    const body = document.body;
    const utenteAttivoId = body.getAttribute('data-nome-utente-loggato') || 'default';
    const nomeUtenteLoggato = parseInt(body.getAttribute('data-utente-attivo-id'), 10);


    fetch('/chat/messaggi/' + utenteAttivoId)
        .then(res => res.json())
        .then(messaggi => {
            const chatBox = document.querySelector('.chat-box');
            chatBox.innerHTML = '';
            messaggi.forEach(m => {
                const div = document.createElement('div');
                div.classList.add('message');
                if (m.mittente === nomeUtenteLoggato) {
                    div.classList.add('sent');
                } else {
                    div.classList.add('received');
                }
                div.innerHTML = `
                <p><strong>${m.mittente}:</strong></p>
                <p>${m.contenuto}</p>
                <span class="timestamp">${new Date(m.dataOra).toLocaleString()}</span>
              `;
                chatBox.appendChild(div);
            });
            chatBox.scrollTop = chatBox.scrollHeight;
        })
        .catch(console.error);
}


window.onload = function () {
    caricaMessaggi();
};

window.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('formMessaggio');
  if (!form) return; 

  form.addEventListener('submit', function (e) {
    e.preventDefault();
    const data = new FormData(form);

    fetch(form.action, {
      method: form.method,
      body: data
    }).then(response => {
      if (!response.ok) {
        alert('Errore nell\'invio del messaggio');
        return;
      }
      form.querySelector('textarea').value = '';
      caricaMessaggi();
    }).catch(err => {
      console.error('Errore:', err);
      alert('Errore nella comunicazione con il server');
    });
  });
});
