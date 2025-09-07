export interface SpotSummary {
  id: number;
  name: string;
  description: string;
  parkName: string;
  region: string;
  lat: number;
  lon: number;
  feeAud: number;
  petAllowed: boolean;
  amenities: string[];
  feeBucket: string;
}

export interface SpotDetails {
  id: number;
  name: string;
  description: string;
  parkName: string;
  region: string;
  authority: string;
  websiteUrl: string;
  lat: number;
  lon: number;
  feeAud: number;
  petAllowed: boolean;
  bookable: boolean;
  amenities: string[];
  updatedAt: string;
}

export interface SpotSearchResponse {
  spots: SpotSummary[];
  totalElements: number;
  totalPages: number;
  currentPage: number;
  size: number;
}

export interface PlanRequest {
  userId: string;
  preferredSpots?: number[];
  startDate: string;
  endDate: string;
  pax: number;
  requiredAmenities?: string[];
  petFriendly?: boolean;
  region?: string;
  maxDistance?: number;
  notes?: string;
}

export interface PlanResponse {
  requestId: string;
  status: string;
  message?: string;
}

export interface WeatherWindow {
  windowId: string;
  spotId: number;
  spotName: string;
  startTime: string;
  endTime: string;
  score: number;
  weatherSummary: string;
  temperature: number;
  precipitationChance: number;
  windSpeed: number;
  uvIndex: number;
}

export interface Alert {
  id: number;
  parkName: string;
  severity: string;
  title: string;
  summary: string;
  startsAt: string;
  endsAt: string;
  source: string;
  url: string;
  fetchedAt: string;
}
