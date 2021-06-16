import {Component, OnInit} from '@angular/core';
import {CartService} from "../services/CartService";
import {HttpProductsService} from "../services/HttpProductsService";
import {HttpOrdersService} from "../services/HttpOrdersService";
import {HttpUsersService} from "../services/HttpUsersService";
import {Router} from "@angular/router";

@Component({
    selector: 'app-checkout',
    templateUrl: './checkout.component.html',
    styles: []
})
export class CheckoutComponent implements OnInit {
    public cartSummary = {
        totalPrice: 0,
        deliveryDate: '0000-00-00',
        products: []
    }

    constructor(
        private cartService: CartService,
        private productsService: HttpProductsService,
        private ordersService: HttpOrdersService,
        private usersService: HttpUsersService,
        private router: Router
    ) {
    }

    ngOnInit(): void {
        console.log(this.usersService.isLoggedIn());
        if (!this.usersService.isLoggedIn()) {
            this.router.navigate(['logowanie']);
        }
        this.calculateDeliveryDate();
        this.productsList();
    }

    productsList() {
        this.cartSummary.products = [];
        this.cartSummary.totalPrice = 0;

        let cart =this.cartService.getCart();
        if (cart != null) {
            cart.products.forEach(product => {
                this.productsService.getById(product.productId).subscribe(response => {
                    this.cartSummary.products.push({
                        id: response.id,
                        quantity: product.quantity,
                        name: response.name,
                        thumbnail: response.thumbnail,
                        price: response.price
                    });
                    this.cartSummary.totalPrice += response.price * product.quantity;
                });
            });
        }
    }

    calculateDeliveryDate() {
        let today = new Date();
        let deliveryDate = new Date(today);
        let deliveryTime = 1;
        if (today.getHours() >= 11) {
            deliveryTime = 2;
        }
        deliveryDate.setDate(deliveryDate.getDate() + deliveryTime);
        let month: any = deliveryDate.getMonth() + 1;
        if (month < 10) {
            month = `0${month}`;
        }
        let day: any = deliveryDate.getDate();
        if (day < 10) {
            day = `0${day}`;
        }
        this.cartSummary.deliveryDate = `${deliveryDate.getFullYear()}-${month}-${day}`;
    }

    getPrice(totalPrice: number) {
        return totalPrice.toFixed(2) + ' ZŁ';
    }

    pay() {
        let products = {};
        this.cartSummary.products.forEach(product => {
            products[product.id] = product.quantity;
        })
        this.ordersService.create(products).subscribe(response => console.log(response));
        // this.cartService.clear();
    }

    decreaseQuantity(id) {
        this.cartService.removeProduct(id);
        this.productsList();
    }

    increaseQuantity(id) {
        this.cartService.addProduct(id);
        this.productsList();
    }
}
