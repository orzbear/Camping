import React from 'react';
import { Search, MapPin, Filter } from 'lucide-react';

interface FiltersProps {
  query: string;
  setQuery: (query: string) => void;
  region: string;
  setRegion: (region: string) => void;
  petAllowed: boolean | null;
  setPetAllowed: (petAllowed: boolean | null) => void;
  onSearch: () => void;
}

export const Filters: React.FC<FiltersProps> = ({
  query,
  setQuery,
  region,
  setRegion,
  petAllowed,
  setPetAllowed,
  onSearch,
}) => {
  const regions = [
    'All Regions',
    'New South Wales',
    'Victoria',
    'Queensland',
    'South Australia',
    'Western Australia',
    'Tasmania',
    'Northern Territory',
    'Australian Capital Territory',
  ];

  return (
    <div className="card mb-6">
      <div className="flex items-center space-x-2 mb-4">
        <Filter className="h-5 w-5 text-primary-600" />
        <h2 className="text-lg font-semibold text-secondary-900">Search & Filters</h2>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        <div>
          <label className="block text-sm font-medium text-secondary-700 mb-2">
            Search
          </label>
          <div className="relative">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-secondary-400" />
            <input
              type="text"
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              placeholder="Search campsites..."
              className="input pl-10"
            />
          </div>
        </div>

        <div>
          <label className="block text-sm font-medium text-secondary-700 mb-2">
            Region
          </label>
          <select
            value={region}
            onChange={(e) => setRegion(e.target.value)}
            className="input"
          >
            {regions.map((reg) => (
              <option key={reg} value={reg === 'All Regions' ? '' : reg}>
                {reg}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label className="block text-sm font-medium text-secondary-700 mb-2">
            Pet Friendly
          </label>
          <select
            value={petAllowed === null ? '' : petAllowed.toString()}
            onChange={(e) => {
              const value = e.target.value;
              setPetAllowed(value === '' ? null : value === 'true');
            }}
            className="input"
          >
            <option value="">All</option>
            <option value="true">Pet Friendly</option>
            <option value="false">No Pets</option>
          </select>
        </div>

        <div className="flex items-end">
          <button
            onClick={onSearch}
            className="btn-primary w-full flex items-center justify-center space-x-2"
          >
            <Search className="h-4 w-4" />
            <span>Search</span>
          </button>
        </div>
      </div>
    </div>
  );
};
