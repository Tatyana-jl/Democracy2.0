import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFinanceReport, defaultValue } from 'app/shared/model/finance-report.model';

export const ACTION_TYPES = {
  FETCH_FINANCEREPORT_LIST: 'financeReport/FETCH_FINANCEREPORT_LIST',
  FETCH_FINANCEREPORT: 'financeReport/FETCH_FINANCEREPORT',
  CREATE_FINANCEREPORT: 'financeReport/CREATE_FINANCEREPORT',
  UPDATE_FINANCEREPORT: 'financeReport/UPDATE_FINANCEREPORT',
  DELETE_FINANCEREPORT: 'financeReport/DELETE_FINANCEREPORT',
  RESET: 'financeReport/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFinanceReport>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type FinanceReportState = Readonly<typeof initialState>;

// Reducer

export default (state: FinanceReportState = initialState, action): FinanceReportState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FINANCEREPORT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FINANCEREPORT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FINANCEREPORT):
    case REQUEST(ACTION_TYPES.UPDATE_FINANCEREPORT):
    case REQUEST(ACTION_TYPES.DELETE_FINANCEREPORT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_FINANCEREPORT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FINANCEREPORT):
    case FAILURE(ACTION_TYPES.CREATE_FINANCEREPORT):
    case FAILURE(ACTION_TYPES.UPDATE_FINANCEREPORT):
    case FAILURE(ACTION_TYPES.DELETE_FINANCEREPORT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_FINANCEREPORT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_FINANCEREPORT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FINANCEREPORT):
    case SUCCESS(ACTION_TYPES.UPDATE_FINANCEREPORT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FINANCEREPORT):
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

const apiUrl = 'api/finance-reports';

// Actions

export const getEntities: ICrudGetAllAction<IFinanceReport> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FINANCEREPORT_LIST,
    payload: axios.get<IFinanceReport>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IFinanceReport> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FINANCEREPORT,
    payload: axios.get<IFinanceReport>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFinanceReport> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FINANCEREPORT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFinanceReport> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FINANCEREPORT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFinanceReport> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FINANCEREPORT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
