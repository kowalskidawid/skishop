import {Component, OnInit} from '@angular/core';
import {HttpUsersService} from "../services/HttpUsersService";
import {Router} from "@angular/router";

@Component({
    selector: 'app-logout',
    template: ``,
    styles: []
})
export class LogoutComponent implements OnInit {

    constructor(private usersService: HttpUsersService, private router: Router) {
    }

    ngOnInit(): void {
        this.usersService.logout();
        this.router.navigate(['/']);
    }
}
