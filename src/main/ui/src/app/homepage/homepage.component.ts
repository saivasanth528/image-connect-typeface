import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent {
  username: string | null | undefined;

  constructor(private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.username = params.get('username');
      console.log(this.username);
    });
  }

  navigateToOwned() {
    this.router.navigate(['/users', this.username, 'owned']);
  }

  navigateToShared() {
    this.router.navigate(['/users', this.username, 'shared']);
  }

  isPathActive(segment: string): boolean {
    return this.router.url.includes(segment);
  }
  
}
