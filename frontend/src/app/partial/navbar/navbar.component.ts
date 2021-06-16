import {Component, OnInit} from '@angular/core';
import {CartService} from "../../services/CartService";
import {HttpUsersService} from "../../services/HttpUsersService";
import {FormBuilder, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
    selector: 'app-navbar',
    templateUrl: 'navbar.component.html',
    styleUrls: [
        './navbar.component.css'
    ]
})
export class NavbarComponent implements OnInit {
    public productsInCart = 0;
    public userDetails: any;
    public searchForm: any;

    constructor(private cartService: CartService, private userService: HttpUsersService, private formBuilder: FormBuilder, private router: Router) {
    }

    ngOnInit(): void {
        this.productsInCart = this.cartService.count();
        this.cartService.productsInCart.subscribe(quantity => {
            this.productsInCart = Number(quantity);
        });
        this.userDetails = this.userService.getUserDetails();
        this.userService.getLoggedUser.subscribe(isLogged => {
            this.userDetails = isLogged;
        });

        this.searchForm = this.formBuilder.group({
            query: ['', Validators.required],
        });
    }

    search() {
        this.router.navigate(['szukaj', this.searchForm.get('query').value]);
    }
}
