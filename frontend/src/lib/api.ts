import axios from 'axios';
import { SpotSearchResponse, SpotDetails, PlanRequest, PlanResponse, Alert } from './types';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const spotApi = {
  search: async (params: {
    query?: string;
    region?: string;
    petAllowed?: boolean;
    amenities?: string;
    lat?: number;
    lon?: number;
    radius?: number;
    page?: number;
    size?: number;
  }): Promise<SpotSearchResponse> => {
    const response = await api.get('/spots/search', { params });
    return response.data;
  },

  getDetails: async (id: number): Promise<SpotDetails> => {
    const response = await api.get(`/spots/${id}`);
    return response.data;
  },

  getForecast: async (id: number): Promise<any> => {
    const response = await api.get(`/spots/${id}/forecast`);
    return response.data;
  },
};

export const planApi = {
  create: async (request: PlanRequest): Promise<PlanResponse> => {
    const response = await api.post('/plan', request);
    return response.data;
  },
};

export const alertApi = {
  getAlerts: async (params: {
    parkId?: number;
    severity?: string;
    limit?: number;
  }): Promise<Alert[]> => {
    const response = await api.get('/alerts', { params });
    return response.data;
  },
};

export default api;
