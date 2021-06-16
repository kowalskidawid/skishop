import {Component, OnInit} from '@angular/core';
import {HttpProductsService} from "../services/HttpProductsService";
import {ActivatedRoute, Router} from "@angular/router";
import {CartService} from "../services/CartService";

@Component({
    selector: 'app-search',
    templateUrl: 'search.component.html',
    styles: []
})
export class SearchComponent implements OnInit {
    public response;

    constructor(private productsService: HttpProductsService, private activatedRoute: ActivatedRoute, private cartService: CartService, private router: Router) {
    }

    ngOnInit(): void {
        let query = this.activatedRoute.snapshot.paramMap.get("query");
        this.productsService.search(query).subscribe(response => {
            this.response = response;
        });
        this.activatedRoute.paramMap.subscribe(map => {
            let query = this.activatedRoute.snapshot.paramMap.get("query");
            this.productsService.search(query).subscribe(response => {
                this.response = response;
            });
        });
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
