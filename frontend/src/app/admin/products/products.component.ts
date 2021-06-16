import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HttpProductsService} from "../../services/HttpProductsService";
import {CartService} from "../../services/CartService";

@Component({
    selector: 'app-products',
    templateUrl: 'products.component.html',
    styles: []
})
export class ProductsComponent implements OnInit {
    public response = {
        count: 0,
        products: []
    };
    private page;
    private categoryId;

    constructor(
        private activatedRoute: ActivatedRoute,
        private productsService: HttpProductsService,
        private cartService: CartService
    ) {
    }

    ngOnInit(): void {
        this.categoryId = Number(this.activatedRoute.snapshot.paramMap.get("categoryId"));
        this.page = Number(this.activatedRoute.snapshot.paramMap.get("page"));
        this.getList();
    }

    getList() {
        if (this.page == 0) {
            this.page = 1;
        }

        if (this.categoryId > 0) {
            this.productsService.getProductsInCategory(this.categoryId, this.page).subscribe(response => {
                this.response = response;
            });
        } else {
            this.productsService.getLastProducts(this.page, 20).subscribe(response => {
                this.response = response;
            });
        }
    }

    getPrice(totalPrice: number) {
        return totalPrice.toFixed(2) + ' ZŁ';
    }

    delete(productId: number) {
        this.productsService.deleteById(productId).subscribe(response => {
            if (response.deleted) {
                this.getList();
            }
        });
    }
}
