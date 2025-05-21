package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import it.uniroma3.siw.model.Avvistamento;
import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.model.Segnalazione;
import it.uniroma3.siw.repository.AvvistamentoRepository;
import it.uniroma3.siw.repository.DenunciaRepository;
import it.uniroma3.siw.repository.SegnalazioneRepository;

@Service
public class SegnalazioneService {

    private final AvvistamentoRepository avvistamentoRepository;
    private final DenunciaRepository denunciaRepository;
    private final SegnalazioneRepository segnalazioneRepository;

    public SegnalazioneService(AvvistamentoRepository avvistamentoRepository, DenunciaRepository denunciaRepository,
            SegnalazioneRepository segnalazioneRepository) {
        this.avvistamentoRepository = avvistamentoRepository;
        this.denunciaRepository = denunciaRepository;
        this.segnalazioneRepository = segnalazioneRepository;
    }

    public Optional<Avvistamento> getAvvistamentoById(Long id) {
        return avvistamentoRepository.findById(id);
    }

    public Optional<Denuncia> getDenunciaById(Long id) {
        return denunciaRepository.findById(id);
    }

    public Optional<Segnalazione> getSegnalazioneById(Long id) {
        return segnalazioneRepository.findById(id);
    }

    public List<Denuncia> getDenunceSimiliPerRazza(String razza, Long idDaEscludere) {
        return denunciaRepository.findByRazzaAndIdNot(razza, idDaEscludere);
    }

    public List<Denuncia> getDenunceVicinePerSpecieERazza(
            String specie, String razza, double lat, double lng, double raggioKm) {

        return denunciaRepository.findDenunceBySpecieAndRazzaAndDistanza(
                specie, razza, lat, lng, raggioKm);
    }

    public List<Avvistamento> getAvvistamentiViciniPerSpecieERazza(
            String specie, String razza, double lat, double lng, double raggioKm) {

        return avvistamentoRepository.findAvvistamentiBySpecieAndRazzaAndDistanza(
                specie, razza, lat, lng, raggioKm);
    }

    public void eliminaById(Long id) {
        segnalazioneRepository.deleteById(id);
    }

    public void aggiorna(Long id, Segnalazione segnalazioneModificata) {
        Optional<Segnalazione> optionalEsistente = segnalazioneRepository.findById(id);

        if (optionalEsistente.isPresent()) {
            Segnalazione esistente = optionalEsistente.get();

            // Campi comuni
            esistente.setRazza(segnalazioneModificata.getRazza());
            esistente.setLuogo(segnalazioneModificata.getLuogo());
            esistente.setFoto(segnalazioneModificata.getFoto());
            esistente.setDataOra(segnalazioneModificata.getDataOra());
            esistente.setCodStatus(segnalazioneModificata.getCodStatus());

            // Controllo tipo specifico
            if (esistente instanceof Denuncia && segnalazioneModificata instanceof Denuncia) {
                Denuncia esistenteDenuncia = (Denuncia) esistente;
                Denuncia modDenuncia = (Denuncia) segnalazioneModificata;

                esistenteDenuncia.setNome(modDenuncia.getNome());
                esistenteDenuncia.setEta(modDenuncia.getEta());
                esistenteDenuncia.setPremioOfferto(modDenuncia.getPremioOfferto());

            } else if (esistente instanceof Avvistamento && segnalazioneModificata instanceof Avvistamento) {
                Avvistamento esistenteAvv = (Avvistamento) esistente;
                Avvistamento modAvv = (Avvistamento) segnalazioneModificata;

                esistenteAvv.setStatoSalute(modAvv.getStatoSalute());
                esistenteAvv.setAzioniIntraprese(modAvv.getAzioniIntraprese());
            }

            segnalazioneRepository.save(esistente);
        } else {
            throw new IllegalArgumentException("Segnalazione con ID " + id + " non trovata.");
        }
    }

    public void save(Segnalazione segnalazione) {
        segnalazioneRepository.save(segnalazione);
    }

}
