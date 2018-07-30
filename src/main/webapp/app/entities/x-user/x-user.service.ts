import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IXUser } from 'app/shared/model/x-user.model';

type EntityResponseType = HttpResponse<IXUser>;
type EntityArrayResponseType = HttpResponse<IXUser[]>;

@Injectable({ providedIn: 'root' })
export class XUserService {
    private resourceUrl = SERVER_API_URL + 'api/x-users';

    constructor(private http: HttpClient) {}

    create(xUser: IXUser): Observable<EntityResponseType> {
        return this.http.post<IXUser>(this.resourceUrl, xUser, { observe: 'response' });
    }

    update(xUser: IXUser): Observable<EntityResponseType> {
        return this.http.put<IXUser>(this.resourceUrl, xUser, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IXUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IXUser[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
