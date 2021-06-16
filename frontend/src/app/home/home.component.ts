import {Component, OnInit} from '@angular/core';
import {HttpProductsService} from "../services/HttpProductsService";
import {CartService} from "../services/CartService";
import {HttpCategoriesService} from "../services/HttpCategoriesService";

@Component({
    selector: 'app-home',
    templateUrl: 'home.component.html',
    styles: []
})
export class HomeComponent implements OnInit {
    public response = {
        products: [],
        categories: []
    };

    constructor(private productService: HttpProductsService, private cartService: CartService, private categoryService: HttpCategoriesService) {
    }

    ngOnInit(): void {
        this.productService.getLastProducts(1).subscribe(response => {
            this.response.products = response.products;
        });

        this.categoryService.getAll().subscribe(response => {
            this.response.categories = response.categories;
        })
    }

    addToCart(productId) {
        this.cartService.addProduct(productId);
    }

    productThumbnail(thumbnail: string): string {
        if (thumbnail == null) {
            thumbnail = '/assets/default.jpeg';
        }

        return thumbnail;
    }

    getPrice(totalPrice: number) {
        return totalPrice.toFixed(2) + ' ZŁ';
    }
}
