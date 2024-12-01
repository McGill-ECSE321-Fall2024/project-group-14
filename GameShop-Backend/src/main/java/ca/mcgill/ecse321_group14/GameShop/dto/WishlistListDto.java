package ca.mcgill.ecse321_group14.GameShop.dto;

import java.util.List;

public class WishlistListDto {
    private List<WishlistDto> wishlists;

    public WishlistListDto(List<WishlistDto> wishlists) {
        this.wishlists = wishlists;
    }

    public List<WishlistDto> getWishlists() {
        return wishlists;
    }

    public void setWishlists(List<WishlistDto> wishlists) {
        this.wishlists = wishlists;
    }

    public WishlistListDto() {
    }
}
