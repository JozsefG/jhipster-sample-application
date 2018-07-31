import { IXTeam } from 'app/shared/model//x-team.model';
import { IXUser } from 'app/shared/model//x-user.model';

export interface IXGroup {
    id?: number;
    name?: string;
    xTeam?: IXTeam;
    xusers?: IXUser[];
}

export class XGroup implements IXGroup {
    constructor(public id?: number, public name?: string, public xTeam?: IXTeam, public xusers?: IXUser[]) {}
}
