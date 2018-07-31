import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IXGroup } from 'app/shared/model/x-group.model';

type EntityResponseType = HttpResponse<IXGroup>;
type EntityArrayResponseType = HttpResponse<IXGroup[]>;

@Injectable({ providedIn: 'root' })
export class XGroupService {
    private resourceUrl = SERVER_API_URL + 'api/x-groups';

    constructor(private http: HttpClient) {}

    create(xGroup: IXGroup): Observable<EntityResponseType> {
        return this.http.post<IXGroup>(this.resourceUrl, xGroup, { observe: 'response' });
    }

    update(xGroup: IXGroup): Observable<EntityResponseType> {
        return this.http.put<IXGroup>(this.resourceUrl, xGroup, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IXGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IXGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
