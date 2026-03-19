package org.example.swamcappugilemmo.BusinessLogic.DTO;

import org.example.swamcappugilemmo.DomainModel.Subscription;

// In org.example.swamcappugilemmo.BusinessLogic.ServiceLayer
public class SubscriptionDTO {
    private String type;
    private String price;
    private String startDate;
    private String endDate;

    // Costruttore che accetta l'oggetto del Domain Model
    public SubscriptionDTO(Subscription s) {
        this.type = s.getType().name(); // o un metodo per avere il nome dell'enum
        this.price = s.getPrice(); // recupera il prezzo dal tipo o dal campo
        this.startDate = s.isActive() ? "Attivo" : "Scaduto"; // esempio di logica
    }
    public String getType() { return type; }
    public String getPrice() { return price; }}