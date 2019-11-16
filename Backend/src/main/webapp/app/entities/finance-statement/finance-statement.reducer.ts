import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFinanceStatement, defaultValue } from 'app/shared/model/finance-statement.model';

export const ACTION_TYPES = {
  FETCH_FINANCESTATEMENT_LIST: 'financeStatement/FETCH_FINANCESTATEMENT_LIST',
  FETCH_FINANCESTATEMENT: 'financeStatement/FETCH_FINANCESTATEMENT',
  CREATE_FINANCESTATEMENT: 'financeStatement/CREATE_FINANCESTATEMENT',
  UPDATE_FINANCESTATEMENT: 'financeStatement/UPDATE_FINANCESTATEMENT',
  DELETE_FINANCESTATEMENT: 'financeStatement/DELETE_FINANCESTATEMENT',
  RESET: 'financeStatement/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFinanceStatement>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type FinanceStatementState = Readonly<typeof initialState>;

// Reducer

export default (state: FinanceStatementState = initialState, action): FinanceStatementState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FINANCESTATEMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FINANCESTATEMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FINANCESTATEMENT):
    case REQUEST(ACTION_TYPES.UPDATE_FINANCESTATEMENT):
    case REQUEST(ACTION_TYPES.DELETE_FINANCESTATEMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_FINANCESTATEMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FINANCESTATEMENT):
    case FAILURE(ACTION_TYPES.CREATE_FINANCESTATEMENT):
    case FAILURE(ACTION_TYPES.UPDATE_FINANCESTATEMENT):
    case FAILURE(ACTION_TYPES.DELETE_FINANCESTATEMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_FINANCESTATEMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_FINANCESTATEMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FINANCESTATEMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_FINANCESTATEMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FINANCESTATEMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/finance-statements';

// Actions

export const getEntities: ICrudGetAllAction<IFinanceStatement> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FINANCESTATEMENT_LIST,
    payload: axios.get<IFinanceStatement>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IFinanceStatement> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FINANCESTATEMENT,
    payload: axios.get<IFinanceStatement>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFinanceStatement> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FINANCESTATEMENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFinanceStatement> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FINANCESTATEMENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFinanceStatement> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FINANCESTATEMENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
