import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { createInjectableType } from "@angular/compiler";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { catchError, Observable, throwError } from "rxjs";
import { JwtClientService } from "../services/jwt-client.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        if (req.headers.get("No-Auth") == 'True') {
            return next.handle(req.clone());
        }
        const token = this.jwtService.getToken();

        req = this.addToken(req, token);
        return next.handle(req).pipe(
            catchError(
                (err: HttpErrorResponse) => {
                    if (err.status === 401) {
                        this.router.navigate(['/login']);
                    } else if (err.status === 403) {
                        this.router.navigate(['/login']);
                        return throwError("Something went wrong, Please try again");
                    }
                    return throwError("Some thing is wrong");
                })
        );

    }

    private addToken(req: HttpRequest<any>, token: string) {
        return req.clone({
            setHeaders: {
                Authorization: `Bearer ${token}`
            }
        });
    }
    constructor(private router: Router,
        private jwtService: JwtClientService) { }

}