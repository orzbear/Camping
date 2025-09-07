import React, { useState, useEffect } from 'react';
import { alertApi } from '../lib/api';
import { Alert } from '../lib/types';
import { AlertTriangle, ExternalLink, Calendar, MapPin, Loader2 } from 'lucide-react';

export const Alerts: React.FC = () => {
  const [alerts, setAlerts] = useState<Alert[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [filter, setFilter] = useState({
    severity: '',
    limit: 20,
  });

  const fetchAlerts = async () => {
    setLoading(true);
    setError(null);
    
    try {
      const data = await alertApi.getAlerts({
        severity: filter.severity || undefined,
        limit: filter.limit,
      });
      setAlerts(data);
    } catch (err) {
      setError('Failed to load alerts. Please try again.');
      console.error('Alerts error:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAlerts();
  }, [filter]);

  const getSeverityColor = (severity: string) => {
    switch (severity.toLowerCase()) {
      case 'high':
      case 'severe':
        return 'text-red-600 bg-red-100';
      case 'medium':
      case 'moderate':
        return 'text-yellow-600 bg-yellow-100';
      case 'low':
      case 'minor':
        return 'text-blue-600 bg-blue-100';
      default:
        return 'text-secondary-600 bg-secondary-100';
    }
  };

  const getSeverityIcon = (severity: string) => {
    switch (severity.toLowerCase()) {
      case 'high':
      case 'severe':
        return '🔴';
      case 'medium':
      case 'moderate':
        return '🟡';
      case 'low':
      case 'minor':
        return '🔵';
      default:
        return '⚪';
    }
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('en-AU', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  };

  return (
    <div className="max-w-4xl mx-auto">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-secondary-900 mb-2">
          Park Alerts & Notifications
        </h1>
        <p className="text-secondary-600">
          Stay informed about important updates and conditions in national parks
        </p>
      </div>

      <div className="card mb-6">
        <h2 className="text-lg font-semibold text-secondary-900 mb-4">
          Filter Alerts
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium text-secondary-700 mb-2">
              Severity
            </label>
            <select
              value={filter.severity}
              onChange={(e) => setFilter({ ...filter, severity: e.target.value })}
              className="input"
            >
              <option value="">All Severities</option>
              <option value="high">High</option>
              <option value="medium">Medium</option>
              <option value="low">Low</option>
            </select>
          </div>
          <div>
            <label className="block text-sm font-medium text-secondary-700 mb-2">
              Number of Alerts
            </label>
            <select
              value={filter.limit}
              onChange={(e) => setFilter({ ...filter, limit: parseInt(e.target.value) })}
              className="input"
            >
              <option value={10}>10</option>
              <option value={20}>20</option>
              <option value={50}>50</option>
              <option value={100}>100</option>
            </select>
          </div>
        </div>
      </div>

      {loading && (
        <div className="flex justify-center items-center py-12">
          <Loader2 className="h-8 w-8 animate-spin text-primary-600" />
          <span className="ml-2 text-secondary-600">Loading alerts...</span>
        </div>
      )}

      {error && (
        <div className="bg-red-50 border border-red-200 rounded-lg p-4 mb-6">
          <p className="text-red-800">{error}</p>
        </div>
      )}

      {!loading && !error && (
        <>
          <div className="mb-6">
            <p className="text-secondary-600">
              Showing {alerts.length} alerts
            </p>
          </div>

          {alerts.length === 0 ? (
            <div className="text-center py-12">
              <AlertTriangle className="h-12 w-12 text-secondary-400 mx-auto mb-4" />
              <p className="text-secondary-600 text-lg">
                No alerts found matching your criteria.
              </p>
              <p className="text-secondary-500 mt-2">
                Try adjusting your filter settings.
              </p>
            </div>
          ) : (
            <div className="space-y-4">
              {alerts.map((alert) => (
                <div key={alert.id} className="card">
                  <div className="flex justify-between items-start mb-3">
                    <div className="flex items-start space-x-3">
                      <div className="text-2xl">
                        {getSeverityIcon(alert.severity)}
                      </div>
                      <div>
                        <h3 className="text-lg font-semibold text-secondary-900 mb-1">
                          {alert.title}
                        </h3>
                        {alert.parkName && (
                          <div className="flex items-center space-x-1 text-sm text-secondary-600 mb-2">
                            <MapPin className="h-4 w-4" />
                            <span>{alert.parkName}</span>
                          </div>
                        )}
                      </div>
                    </div>
                    <span className={`px-2 py-1 rounded-full text-xs font-medium ${getSeverityColor(alert.severity)}`}>
                      {alert.severity.toUpperCase()}
                    </span>
                  </div>

                  {alert.summary && (
                    <p className="text-secondary-700 mb-4 leading-relaxed">
                      {alert.summary}
                    </p>
                  )}

                  <div className="flex flex-wrap items-center justify-between text-sm text-secondary-600">
                    <div className="flex items-center space-x-4">
                      {alert.startsAt && (
                        <div className="flex items-center space-x-1">
                          <Calendar className="h-4 w-4" />
                          <span>Starts: {formatDate(alert.startsAt)}</span>
                        </div>
                      )}
                      {alert.endsAt && (
                        <div className="flex items-center space-x-1">
                          <Calendar className="h-4 w-4" />
                          <span>Ends: {formatDate(alert.endsAt)}</span>
                        </div>
                      )}
                    </div>
                    <div className="flex items-center space-x-2">
                      <span>Source: {alert.source}</span>
                      {alert.url && (
                        <a
                          href={alert.url}
                          target="_blank"
                          rel="noopener noreferrer"
                          className="flex items-center space-x-1 text-primary-600 hover:text-primary-700"
                        >
                          <ExternalLink className="h-4 w-4" />
                          <span>More Info</span>
                        </a>
                      )}
                    </div>
                  </div>

                  <div className="mt-3 pt-3 border-t border-secondary-200">
                    <p className="text-xs text-secondary-500">
                      Last updated: {formatDate(alert.fetchedAt)}
                    </p>
                  </div>
                </div>
              ))}
            </div>
          )}
        </>
      )}
    </div>
  );
};
