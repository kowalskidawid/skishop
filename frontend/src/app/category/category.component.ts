import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HttpProductsService} from "../services/HttpProductsService";
import {CartService} from "../services/CartService";

@Component({
    selector: 'app-category',
    templateUrl: './category.component.html',
    styles: []
})
export class CategoryComponent implements OnInit {
    public response;

    constructor(
        private activatedRoute: ActivatedRoute,
        private productsService: HttpProductsService,
        private cartService: CartService
    ) {
    }

    ngOnInit(): void {
        let categoryId = Number(this.activatedRoute.snapshot.paramMap.get("categoryId"));
        if (!isNaN(categoryId)) {
            this.productsService.getProductsInCategory(categoryId, 1).subscribe(response => {
                this.response = response;
            })
        }
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
}
