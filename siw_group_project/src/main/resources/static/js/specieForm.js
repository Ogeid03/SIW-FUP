const razzePerSpecie = {
      "Cane": ["Labrador", "Bulldog", "Pastore Tedesco", "Barboncino"],
      "Gatto": ["Persiano", "Siamese", "Maine Coon", "Europeo"],
      "Pappagallo": ["Cacatua", "Ara", "Inseparabile", "Calopsitta"],
      "Roditore": ["Topo", "Cavia", "Porcellino D'India", "Coniglio"],
      "Rettile": ["Tartaruga", "Serpente", "Iguana", "Coccodrillo"]
    };

    document.getElementById("specie").addEventListener("change", function () {
      const specie = this.value;
      const razzaSelect = document.getElementById("razza");

      // Svuota le opzioni precedenti
      razzaSelect.innerHTML = '<option value="">-- Seleziona una razza --</option>';

      if (razzePerSpecie[specie]) {
        razzePerSpecie[specie].forEach(razza => {
          const option = document.createElement("option");
          option.value = razza;
          option.textContent = razza;
          razzaSelect.appendChild(option);
        });
      }
    });