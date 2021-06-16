import {Injectable, Output} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';
import {isNull} from "@angular/compiler/src/output/output_ast";

@Injectable({
    providedIn: 'root'
})
export class CartService {
    @Output() public productsInCart = new Subject();

    constructor(private http: HttpClient) {
    }

    addProduct(productId: number) {
        let cart = this.getCart();
        if (cart !== null) {
            let quantityIncreased = false;
            for (let product of cart.products) {
                if (product.productId == productId) {
                    product.quantity++;
                    quantityIncreased = true;
                    break;
                }
            }

            if (!quantityIncreased) {
                cart.products.push({
                    productId: productId,
                    quantity: 1
                });
                console.log('increase');
                this.updateCounter();
            }
        } else {
            cart = {
                products: [{
                    productId: productId,
                    quantity: 1
                }]
            };
            this.updateCounter();
        }
        localStorage.setItem('cart', JSON.stringify(cart));
    }

    getCart() {
        return JSON.parse(localStorage.getItem('cart'));
    }

    count() {
        let cart = this.getCart();
        let count = 0;
        if (cart !== null) {
            count = cart.products.length;
        }
        return count;
    }

    removeProduct(productId: number) {
        let cart = this.getCart();
        if (cart !== null) {
            let productIndex = -1;
            for (let product of cart.products) {
                productIndex++;
                if (product.productId == productId) {
                    product.quantity--;

                    if (product.quantity <= 0) {
                        delete cart.products[productIndex];
                        this.updateCounter();
                    }
                    break;
                }
            }
        }

        localStorage.setItem('cart', JSON.stringify(cart));
    }

    clear() {
        localStorage.removeItem('cart');
    }

    updateCounter() {
        this.productsInCart.next(this.count());
    }
}
