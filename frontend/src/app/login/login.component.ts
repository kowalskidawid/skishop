import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {HttpUsersService} from "../services/HttpUsersService";
import {Router} from "@angular/router";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styles: []
})
export class LoginComponent implements OnInit {
    public userForm: any;
    public invalidForm = false;
    public serverError = false;

    constructor(private formBuilder: FormBuilder, private usersService: HttpUsersService, private router: Router) {
    }

    ngOnInit(): void {
        this.userForm = this.formBuilder.group({
            email: ['', [Validators.required, Validators.email]],
            password: ['', Validators.required],
        });
    }

    login() {
        if (this.userForm.valid) {
            let user = {
                email: this.userForm.get('email').value,
                password: this.userForm.get('password').value,
            };
            this.usersService.login(user).subscribe(response => {
                if (response.logged) {
                    this.usersService.storeUserDetails(response.user, response.token);
                    this.router.navigate(['/']);
                } else {
                    this.serverError = true;
                }
            });
        } else {
            this.invalidForm = true;
        }
    }
}
