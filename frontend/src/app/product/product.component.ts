import {Component, OnInit} from '@angular/core';
import {HttpProductsService} from "../services/HttpProductsService";
import {ActivatedRoute} from "@angular/router";
import {CartService} from "../services/CartService";

@Component({
    selector: 'app-product',
    templateUrl: './product.component.html',
    styles: []
})
export class ProductComponent implements OnInit {
    public product;

    constructor(
        private activatedRoute: ActivatedRoute,
        private productsService: HttpProductsService,
        private cartService: CartService
    ) {
    }

    ngOnInit(): void {
        let productId = Number(this.activatedRoute.snapshot.paramMap.get("productId"));
        if (!isNaN(productId)) {
            this.productsService.getById(productId).subscribe(response => {
                this.product = response;
            })
        }
    }

    priceFormat(price:number) :string {
        return `${price.toFixed(2)} ZŁ`;
    }
}
