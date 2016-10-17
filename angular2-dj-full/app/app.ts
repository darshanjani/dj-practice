import { bootstrap } from "@angular/platform-browser-dynamic";
import { Login } from './components/LoginComponent'
import { HTTP_PROVIDERS } from '@angular/http'

bootstrap(Login, [HTTP_PROVIDERS]);