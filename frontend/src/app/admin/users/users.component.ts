import {Component, OnInit} from '@angular/core';
import {HttpUsersService} from "../../services/HttpUsersService";

@Component({
    selector: 'app-users',
    templateUrl: 'users.component.html',
    styles: []
})
export class UsersComponent implements OnInit {
    public response = {
        count: 0,
        users: []
    }

    constructor(private usersService: HttpUsersService) {
    }

    ngOnInit(): void {
        this.refreshList();
    }

    getRuleText(rule: string) {
        let text = 'Użytkownik';
        if (rule == 'SELLER') {
            text = 'Sprzedawca';
        } else if (rule == 'ADMIN') {
            text = 'Administrator';
        }

        return text;
    }

    delete(id: number) {
        this.usersService.delete(id).subscribe(response => {
            this.refreshList();
        });
    }

    changeRule(id: number, rule: string) {
        this.usersService.changeRule(id, rule).subscribe(response => {
            this.refreshList();
        });
    }

    private refreshList() {
        this.usersService.getAll().subscribe(response => {
            this.response = response;
        })
    }
}
