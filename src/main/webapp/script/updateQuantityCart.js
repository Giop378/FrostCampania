function updateQuantita(idProdotto) {
    var nuovaQuantita = document.getElementById(idProdotto).value;

    fetch("update-cart-quantity?idProdotto=" + encodeURIComponent(idProdotto) + "&nuovaQuantita=" + encodeURIComponent(nuovaQuantita))
        .then(response => {
            if (!response.ok) {
                return response.json().then(error => {
                    throw new Error(error.message);
                });
            }
            // Restituisci il JSON della risposta se la richiesta ha successo
            return response.json();
        })
        .then(data => {
            // Aggiorna la quantità nell'HTML
            document.getElementById(idProdotto).value = data.nuovaQuantita;
        })
        .catch(error => {
            // Mostra un messaggio di errore all'utente
            alert("Errore: " + error.message);
        });
}