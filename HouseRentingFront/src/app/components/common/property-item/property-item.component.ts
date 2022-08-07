import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-property-item',
  templateUrl: './property-item.component.html',
  styleUrls: ['./property-item.component.css']
})
export class PropertyItemComponent implements OnInit {

  @Input() property: any;
  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  viewPropertyDetail(id:number) {
      this.router.navigate([`/property-detail/${id}`])
  }

}
