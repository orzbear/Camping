import React, { useState } from 'react';
import { planApi } from '../lib/api';
import { useSSE } from '../lib/useSSE';
import { WeatherWindow } from '../lib/types';
import { Calendar, Users, MapPin, Send, Loader2, Star, Thermometer, Droplets, Wind } from 'lucide-react';

export const Planner: React.FC = () => {
  const [formData, setFormData] = useState({
    userId: 'demo-user',
    startDate: '',
    endDate: '',
    pax: 2,
    region: '',
    petFriendly: false,
    notes: '',
  });
  
  const [requestId, setRequestId] = useState<string | null>(null);
  const [isPlanning, setIsPlanning] = useState(false);
  const [error, setError] = useState<string | null>(null);
  
  const { data: weatherWindows, isConnected, error: sseError } = useSSE(
    requestId ? `http://localhost:8080/api/plan/stream/${requestId}` : ''
  );

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsPlanning(true);
    setError(null);
    setRequestId(null);

    try {
      const response = await planApi.create({
        ...formData,
        startDate: new Date(formData.startDate).toISOString(),
        endDate: new Date(formData.endDate).toISOString(),
      });
      
      setRequestId(response.requestId);
    } catch (err) {
      setError('Failed to create plan. Please try again.');
      console.error('Plan creation error:', err);
    } finally {
      setIsPlanning(false);
    }
  };

  const getScoreColor = (score: number) => {
    if (score >= 0.8) return 'text-green-600 bg-green-100';
    if (score >= 0.6) return 'text-yellow-600 bg-yellow-100';
    return 'text-red-600 bg-red-100';
  };

  const getScoreLabel = (score: number) => {
    if (score >= 0.8) return 'Excellent';
    if (score >= 0.6) return 'Good';
    if (score >= 0.4) return 'Fair';
    return 'Poor';
  };

  return (
    <div className="max-w-6xl mx-auto">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-secondary-900 mb-2">
          Smart Trip Planner
        </h1>
        <p className="text-secondary-600">
          Get weather-aware recommendations for your camping trip
        </p>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        <div className="card">
          <h2 className="text-xl font-semibold text-secondary-900 mb-6">
            Trip Details
          </h2>
          
          <form onSubmit={handleSubmit} className="space-y-6">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-secondary-700 mb-2">
                  Start Date
                </label>
                <div className="relative">
                  <Calendar className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-secondary-400" />
                  <input
                    type="date"
                    value={formData.startDate}
                    onChange={(e) => setFormData({ ...formData, startDate: e.target.value })}
                    className="input pl-10"
                    required
                  />
                </div>
              </div>

              <div>
                <label className="block text-sm font-medium text-secondary-700 mb-2">
                  End Date
                </label>
                <div className="relative">
                  <Calendar className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-secondary-400" />
                  <input
                    type="date"
                    value={formData.endDate}
                    onChange={(e) => setFormData({ ...formData, endDate: e.target.value })}
                    className="input pl-10"
                    required
                  />
                </div>
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-secondary-700 mb-2">
                Number of People
              </label>
              <div className="relative">
                <Users className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-secondary-400" />
                <input
                  type="number"
                  min="1"
                  max="20"
                  value={formData.pax}
                  onChange={(e) => setFormData({ ...formData, pax: parseInt(e.target.value) })}
                  className="input pl-10"
                  required
                />
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-secondary-700 mb-2">
                Preferred Region
              </label>
              <div className="relative">
                <MapPin className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-secondary-400" />
                <select
                  value={formData.region}
                  onChange={(e) => setFormData({ ...formData, region: e.target.value })}
                  className="input pl-10"
                >
                  <option value="">Any Region</option>
                  <option value="New South Wales">New South Wales</option>
                  <option value="Victoria">Victoria</option>
                  <option value="Queensland">Queensland</option>
                  <option value="South Australia">South Australia</option>
                  <option value="Western Australia">Western Australia</option>
                  <option value="Tasmania">Tasmania</option>
                  <option value="Northern Territory">Northern Territory</option>
                  <option value="Australian Capital Territory">Australian Capital Territory</option>
                </select>
              </div>
            </div>

            <div className="flex items-center space-x-2">
              <input
                type="checkbox"
                id="petFriendly"
                checked={formData.petFriendly}
                onChange={(e) => setFormData({ ...formData, petFriendly: e.target.checked })}
                className="rounded border-secondary-300 text-primary-600 focus:ring-primary-500"
              />
              <label htmlFor="petFriendly" className="text-sm text-secondary-700">
                Pet-friendly campsites only
              </label>
            </div>

            <div>
              <label className="block text-sm font-medium text-secondary-700 mb-2">
                Additional Notes
              </label>
              <textarea
                value={formData.notes}
                onChange={(e) => setFormData({ ...formData, notes: e.target.value })}
                placeholder="Any special requirements or preferences..."
                className="input h-24 resize-none"
                rows={3}
              />
            </div>

            <button
              type="submit"
              disabled={isPlanning || !formData.startDate || !formData.endDate}
              className="btn-primary w-full flex items-center justify-center space-x-2 disabled:opacity-50 disabled:cursor-not-allowed"
            >
              {isPlanning ? (
                <>
                  <Loader2 className="h-4 w-4 animate-spin" />
                  <span>Planning...</span>
                </>
              ) : (
                <>
                  <Send className="h-4 w-4" />
                  <span>Plan My Trip</span>
                </>
              )}
            </button>
          </form>

          {error && (
            <div className="mt-4 bg-red-50 border border-red-200 rounded-lg p-4">
              <p className="text-red-800">{error}</p>
            </div>
          )}

          {sseError && (
            <div className="mt-4 bg-red-50 border border-red-200 rounded-lg p-4">
              <p className="text-red-800">Connection error: {sseError}</p>
            </div>
          )}
        </div>

        <div className="space-y-6">
          {isConnected && (
            <div className="card">
              <div className="flex items-center space-x-2 mb-4">
                <Loader2 className="h-5 w-5 animate-spin text-primary-600" />
                <h2 className="text-xl font-semibold text-secondary-900">
                  Analyzing Weather Windows...
                </h2>
              </div>
              <p className="text-secondary-600">
                Finding the best camping conditions for your trip dates.
              </p>
            </div>
          )}

          {weatherWindows.length > 0 && (
            <div className="card">
              <h2 className="text-xl font-semibold text-secondary-900 mb-4">
                Recommended Weather Windows
              </h2>
              <div className="space-y-4">
                {weatherWindows.map((window: WeatherWindow) => (
                  <div key={window.windowId} className="border border-secondary-200 rounded-lg p-4">
                    <div className="flex justify-between items-start mb-3">
                      <div>
                        <h3 className="font-semibold text-secondary-900">
                          {window.spotName}
                        </h3>
                        <p className="text-sm text-secondary-600">
                          {new Date(window.startTime).toLocaleDateString()} - {new Date(window.endTime).toLocaleDateString()}
                        </p>
                      </div>
                      <div className="flex items-center space-x-2">
                        <span className={`px-2 py-1 rounded-full text-xs font-medium ${getScoreColor(window.score)}`}>
                          <Star className="h-3 w-3 inline mr-1" />
                          {window.score.toFixed(1)}
                        </span>
                        <span className="text-xs text-secondary-500">
                          {getScoreLabel(window.score)}
                        </span>
                      </div>
                    </div>

                    <p className="text-sm text-secondary-700 mb-3">
                      {window.weatherSummary}
                    </p>

                    <div className="grid grid-cols-2 md:grid-cols-4 gap-3 text-sm">
                      <div className="flex items-center space-x-1">
                        <Thermometer className="h-4 w-4 text-secondary-500" />
                        <span className="text-secondary-600">{window.temperature.toFixed(1)}°C</span>
                      </div>
                      <div className="flex items-center space-x-1">
                        <Droplets className="h-4 w-4 text-secondary-500" />
                        <span className="text-secondary-600">{window.precipitationChance.toFixed(0)}%</span>
                      </div>
                      <div className="flex items-center space-x-1">
                        <Wind className="h-4 w-4 text-secondary-500" />
                        <span className="text-secondary-600">{window.windSpeed.toFixed(1)} m/s</span>
                      </div>
                      <div className="flex items-center space-x-1">
                        <span className="text-secondary-500">UV</span>
                        <span className="text-secondary-600">{window.uvIndex.toFixed(1)}</span>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          )}

          {!isConnected && !isPlanning && weatherWindows.length === 0 && (
            <div className="card">
              <h2 className="text-xl font-semibold text-secondary-900 mb-4">
                How It Works
              </h2>
              <div className="space-y-4 text-secondary-600">
                <div className="flex items-start space-x-3">
                  <div className="w-6 h-6 bg-primary-100 text-primary-600 rounded-full flex items-center justify-center text-sm font-medium">
                    1
                  </div>
                  <p>Enter your trip details and preferences</p>
                </div>
                <div className="flex items-start space-x-3">
                  <div className="w-6 h-6 bg-primary-100 text-primary-600 rounded-full flex items-center justify-center text-sm font-medium">
                    2
                  </div>
                  <p>Our AI analyzes weather patterns and campsite availability</p>
                </div>
                <div className="flex items-start space-x-3">
                  <div className="w-6 h-6 bg-primary-100 text-primary-600 rounded-full flex items-center justify-center text-sm font-medium">
                    3
                  </div>
                  <p>Get real-time recommendations with weather scores</p>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};
