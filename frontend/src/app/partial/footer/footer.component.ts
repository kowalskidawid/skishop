import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: 'footer.component.html',
  styles: [
  ]
})
export class FooterComponent implements OnInit {
  currentDate: Date
  constructor() { }

  ngOnInit(): void {
    this.currentDate = new Date();
  }

}
