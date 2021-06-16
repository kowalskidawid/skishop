import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators, NgControl} from "@angular/forms";
import {HttpUsersService} from "../services/HttpUsersService";
import {Router} from "@angular/router";

@Component({
    selector: 'app-register',
    templateUrl: 'register.component.html',
    styles: []
})
export class RegisterComponent implements OnInit {
    public userForm: any;
    public invalidForm = false;
    public serverError = false;

    constructor(private formBuilder: FormBuilder, private usersService: HttpUsersService, private router: Router) {
    }

    ngOnInit(): void {
        this.userForm = this.formBuilder.group({
            first_name: ['', Validators.required],
            last_name: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            phone_number: ['', Validators.required],
            password: ['', Validators.required],
            town: ['', [Validators.required]],
            post_code: ['', [Validators.required]],
            street: ['', [Validators.required]],
        });
    }

    register() {
        if (this.userForm.valid) {
            let user = {
                firstName: this.userForm.get('first_name').value,
                lastName: this.userForm.get('last_name').value,
                password: this.userForm.get('password').value,
                email: this.userForm.get('email').value,
                phone: this.userForm.get('phone_number').value,
                address: {
                    street: this.userForm.get('street').value,
                    zipCode: this.userForm.get('post_code').value,
                    city: this.userForm.get('town').value,
                }
            };
            this.usersService.register(user).subscribe(response => {
                if (response.created) {
                    this.router.navigate(['/logowanie']);
                } else if (response.error) {
                    this.serverError = response.error;
                }
            });
        } else {
            this.invalidForm = true;
        }
    }
}
