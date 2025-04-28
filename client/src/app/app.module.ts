import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import {AppComponent} from './app.component';
import {AppRoutingModule, routes} from './app.routes';
import {BrowserModule} from '@angular/platform-browser';
import {TaskPageComponent} from './task-page/task-page.component';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

@NgModule({
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes),
    TaskPageComponent,
    AppComponent
  ],
  providers: []
})
export class AppModule { }
