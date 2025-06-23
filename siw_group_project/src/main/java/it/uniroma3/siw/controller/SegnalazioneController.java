package it.uniroma3.siw.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import it.uniroma3.siw.model.Avvistamento;
import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.model.Messaggio;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.SegnalazioneService;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class SegnalazioneController {

    @Autowired
    private final SegnalazioneService segnalazioneService;
    @Autowired
    private UtenteService utenteService;

    public SegnalazioneController(SegnalazioneService segnalazioneService, UtenteService utenteService) {
        this.segnalazioneService = segnalazioneService;
        this.utenteService = utenteService;
    }

    @GetMapping("/segnalazioni/{id:[0-9]+}")
public String dettagliSegnalazione(@PathVariable Long id, Model model) {
    Utente utenteLoggato = utenteService.getUtenteAutenticato();
    model.addAttribute("utenteLoggato", utenteLoggato);

    Optional<Avvistamento> avv = segnalazioneService.getAvvistamentoById(id);
    if (avv.isPresent()) {
        Avvistamento avvistamento = avv.get();
        model.addAttribute("segnalazione", avvistamento);
        model.addAttribute("tipo", "avvistamento");

        if (avvistamento.getLatitudine() != null && avvistamento.getLongitudine() != null) {
            List<Denuncia> rilevanti = segnalazioneService.getDenunceVicinePerSpecieERazza(
                    avvistamento.getSpecie(),
                    avvistamento.getRazza(),
                    avvistamento.getLatitudine(),
                    avvistamento.getLongitudine(),
                    5.0
            );
            model.addAttribute("rilevanti", rilevanti);
        } else {
            model.addAttribute("rilevanti", List.of());
        }

        // Solo se l'utente NON è il proprietario
        if (utenteLoggato != null && avvistamento.getCodUtente() != null &&
                !utenteLoggato.getId().equals(avvistamento.getCodUtente().getId())) {
            Messaggio messaggio = new Messaggio();
            messaggio.setCodDestinatario(avvistamento.getCodUtente());
            messaggio.setCodSegnalazione(avvistamento);
            model.addAttribute("messaggio", messaggio);
            model.addAttribute("destinatarioId", avvistamento.getCodUtente().getId());
        }

        return "segnalazione";
    }

    Optional<Denuncia> den = segnalazioneService.getDenunciaById(id);
    if (den.isPresent()) {
        Denuncia denuncia = den.get();
        model.addAttribute("segnalazione", denuncia);
        model.addAttribute("tipo", "denuncia");

        if (denuncia.getLatitudine() != null && denuncia.getLongitudine() != null) {
            List<Avvistamento> simili = segnalazioneService.getAvvistamentiViciniPerSpecieERazza(
                    denuncia.getSpecie(),
                    denuncia.getRazza(),
                    denuncia.getLatitudine(),
                    denuncia.getLongitudine(),
                    5.0
            );
            model.addAttribute("simili", simili);
        } else {
            model.addAttribute("simili", List.of());
        }

        if (utenteLoggato != null && denuncia.getCodUtente() != null &&
                !utenteLoggato.getId().equals(denuncia.getCodUtente().getId())) {
            Messaggio messaggio = new Messaggio();
            messaggio.setCodDestinatario(denuncia.getCodUtente());
            messaggio.setCodSegnalazione(denuncia);
            model.addAttribute("messaggio", messaggio);
            model.addAttribute("destinatarioId", denuncia.getCodUtente().getId());
        }

        return "segnalazione";
    }

    return "error";
}


}
