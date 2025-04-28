import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [FormsModule, RouterModule]
})
export class AppComponent {
  title = 'client';
}
