function confirmDelete(id) {
        document.getElementById('deleteId').value = id;
        document.getElementById('deleteForm').action = '/segnalazioni/elimina/' + id;
        document.getElementById('confirmModal').classList.remove('hidden');
    }

    function closeModal() {
        document.getElementById('confirmModal').classList.add('hidden');
    }