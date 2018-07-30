import { IXTeam } from 'app/shared/model//x-team.model';

export interface IXGroup {
    id?: number;
    name?: string;
    xTeam?: IXTeam;
    xteams?: IXTeam[];
}

export class XGroup implements IXGroup {
    constructor(public id?: number, public name?: string, public xTeam?: IXTeam, public xteams?: IXTeam[]) {}
}
