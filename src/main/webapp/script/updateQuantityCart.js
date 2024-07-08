function updateQuantita(idProdotto) {
    var nuovaQuantita = document.getElementById(idProdotto).value;

    // Costruisci l'oggetto dati da inviare come JSON
    var requestData = {
        idProdotto: idProdotto,
        quantita: nuovaQuantita
    };

    fetch('update-cart-quantity', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData) // Converte l'oggetto in JSON
    })
        .then(response => {
            if (!response.ok) {
                throw new MyServletException('Errore durante l\'aggiornamento della quantità');
            }

        })
        .catch(error => {
            alert("Errore");

        });
}