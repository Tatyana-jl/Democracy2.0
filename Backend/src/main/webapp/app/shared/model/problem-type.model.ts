export interface IProblemType {
  id?: number;
  category?: string;
  problem?: string;
  problemId?: number;
}

export const defaultValue: Readonly<IProblemType> = {};
